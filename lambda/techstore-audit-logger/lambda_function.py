code lambda\techstore-audit-logger\lambda_function.py
import json


def lambda_handler(event, context):
    print("[TechStore Audit] Funcion Lambda iniciada")

    for record in event.get("Records", []):
        try:
            body = json.loads(record.get("body", "{}"))

            accion = body.get("accion", "SIN_ACCION")
            producto_id = body.get("productoId", "SIN_ID")
            nombre = body.get("nombre", "SIN_NOMBRE")
            usuario = body.get("usuario", "SIN_USUARIO")
            fecha = body.get("fecha", "SIN_FECHA")

            print("=======================================================")
            print("[TechStore Audit] NUEVA AUDITORIA DE INVENTARIO")
            print("=======================================================")
            print(f"Accion realizada: {accion}")
            print(f"ID producto: {producto_id}")
            print(f"Nombre producto: {nombre}")
            print(f"Usuario operador: {usuario}")
            print(f"Fecha operacion: {fecha}")
            print("=======================================================")

        except Exception as error:
            print("[TechStore Audit] Error procesando mensaje SQS")
            print(f"Error: {str(error)}")
            print(f"Registro original: {record}")

    return {
        "statusCode": 200,
        "body": json.dumps("Auditoria procesada correctamente")
    }