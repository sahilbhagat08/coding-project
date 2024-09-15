package calendly.dto;

import lombok.Data;

@Data
public class OverlapRequest {
    private Long firstUserId;
    private Long secondUserId;
}

