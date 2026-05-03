package DTO;

import lombok.Data;

@Data
public class TicketDTO {
    private String title;
    private String category;
    private String priority;
    private String summary;
}
