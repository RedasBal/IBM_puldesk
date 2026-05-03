package Services;

import DTO.TicketDTO;
import Services.HuggingFaceService;
import Services.TriageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TriageServiceTest {

    @Mock
    private HuggingFaceService huggingFaceService;

    @InjectMocks
    private TriageService triageService;

    @Test
    void whenCommentIsIssue_shouldReturnTicketDTO() {

        String fakeResponse = "{\"choices\":[{\"message\":{\"content\":\"yes | Login broken | bug | high | User cannot login\"}}]}";
        when(huggingFaceService.analyze(any())).thenReturn(fakeResponse);

        TicketDTO result = triageService.analyze("Login button is broken");

        assertNotNull(result);
        assertEquals("Login broken", result.getTitle());
        assertEquals("bug", result.getCategory());
        assertEquals("high", result.getPriority());
    }

    @Test
    void whenCommentIsCompliment_shouldReturnNull() {
        String fakeResponse = "{\"choices\":[{\"message\":{\"content\":\"no\"}}]}";
        when(huggingFaceService.analyze(any())).thenReturn(fakeResponse);

        TicketDTO result = triageService.analyze("Great app, love it!");

        assertNull(result);
    }

    @Test
    void whenHuggingFaceReturnsNull_shouldReturnNull() {
        when(huggingFaceService.analyze(any())).thenReturn(null);

        TicketDTO result = triageService.analyze("something");

        assertNull(result);
    }
}