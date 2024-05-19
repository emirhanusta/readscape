package notificationservice.service;

import notificationservice.dto.NotificationRequest;
import notificationservice.model.Notification;
import notificationservice.repository.NotificationRepository;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public void sendNotification(NotificationRequest request) {
        Notification notification = Notification.builder()
                .accountId(request.accountId())
                .reviewId(request.reviewId())
                .message(request.message())
                .build();
        notificationRepository.save(notification);
    }
}
