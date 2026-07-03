package cl.techstore.api.service;

import cl.techstore.api.dto.AuditEventDTO;
import cl.techstore.api.model.Producto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

import java.time.Instant;

@Service
public class AuditService {

    private static final Logger log = LoggerFactory.getLogger(AuditService.class);

    private final SqsClient sqsClient;
    private final ObjectMapper objectMapper;

    @Value("${app.aws.sqs.queue-url:}")
    private String queueUrl;

    public AuditService(SqsClient sqsClient, ObjectMapper objectMapper) {
        this.sqsClient = sqsClient;
        this.objectMapper = objectMapper;
    }

    @Async
    public void enviarAuditoria(String accion, Producto producto, String usuario) {
        try {
            AuditEventDTO evento = new AuditEventDTO(
                    accion,
                    producto.getId(),
                    producto.getNombre(),
                    usuario,
                    Instant.now().toString()
            );

            String mensajeJson = objectMapper.writeValueAsString(evento);

            if (queueUrl == null || queueUrl.isBlank()) {
                log.warn("SQS_QUEUE_URL no configurada. Auditoría generada localmente: {}", mensajeJson);
                return;
            }

            SendMessageRequest request = SendMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .messageBody(mensajeJson)
                    .build();

            sqsClient.sendMessage(request);

            log.info("Auditoría enviada a SQS: {}", mensajeJson);

        } catch (Exception e) {
            log.error("Error enviando auditoría a SQS", e);
        }
    }
}