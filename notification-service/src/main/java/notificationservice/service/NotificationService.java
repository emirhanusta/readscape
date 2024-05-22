package notificationservice.service;

import notificationservice.dto.NotificationRequest;
import notificationservice.model.Notification;
import notificationservice.repository.NotificationRepository;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    Logger log = org.slf4j.LoggerFactory.getLogger(NotificationService.class);

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public void sendNotification(NotificationRequest request) {
        log.info("Sending notification for account {} and review {}", request.accountId(), request.reviewId());
        Notification notification = Notification.builder()
                .accountId(request.accountId())
                .reviewId(request.reviewId())
                .message(request.message())
                .build();
        notificationRepository.save(notification);
    }
}
