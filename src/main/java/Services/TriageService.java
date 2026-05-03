package Services;

import DTO.TicketDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TriageService {

    @Autowired
    private HuggingFaceService huggingFaceService;

    public TicketDTO analyze(String commentText) {
        String response = huggingFaceService.analyze(commentText);

        if (response == null || response.isBlank()) {
            return null;
        }
        String content = extractContent(response);

        if (content == null || content.toLowerCase().startsWith("no")) {
            return null;
        }

        String[] parts = content.split("\\|");

        if (parts.length < 5) {
            return null;
        }

        TicketDTO dto = new TicketDTO();
        dto.setTitle(parts[1].trim());
        dto.setCategory(parts[2].trim());
        dto.setPriority(parts[3].trim());
        dto.setSummary(parts[4].trim());

        return dto;
    }

    private String extractContent(String rawResponse) {
        try {
            int start = rawResponse.indexOf("\"content\":\"") + 11;
            int end = rawResponse.indexOf("\"", start);
            return rawResponse.substring(start, end);
        } catch (Exception e) {
            return null;
        }
    }
}