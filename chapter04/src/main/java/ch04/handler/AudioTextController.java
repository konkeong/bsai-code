package ch04.handler;

import java.io.IOException;

import org.springframework.ai.audio.transcription.AudioTranscriptionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import ch04.model.TextToSpeechRequest;
import ch04.service.TextToSpeechService;
import ch04.service.TranscribeService;
import ch04.service.VoiceAssistantService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api")
public class AudioTextController {

    protected final TextToSpeechService textToSpeechService;

    protected final TranscribeService transcribeService;

    protected final VoiceAssistantService voiceAssistantService;

    @Autowired
    public AudioTextController(
            TextToSpeechService textToSpeechService,
            TranscribeService transcribeService,
            VoiceAssistantService voiceAssistantService) {
        this.textToSpeechService = textToSpeechService;
        this.transcribeService = transcribeService;
        this.voiceAssistantService = voiceAssistantService;
    }

    @PostMapping("/tts")
    public ResponseEntity<byte[]> handleTextToSpeech(@RequestBody TextToSpeechRequest request) {
        byte[] speechResult = textToSpeechService.synthesize(request.text());

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=output.mp3");
        headers.add(HttpHeaders.CONTENT_TYPE, "audio/mpeg");
        return ResponseEntity.ok()
                .headers(headers)
                .body(speechResult);
    }

    @PostMapping("/transcribe")
    public ResponseEntity<String> handleAudioUpload(@RequestParam(name = "file") MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body("No file uploaded");
        }

        try {
            AudioTranscriptionResponse response = transcribeService.transcribe(new ByteArrayResource(file.getBytes()));
            return ResponseEntity.ok(response.getResult().getOutput());
        } catch (IOException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("File upload failed");
        }
    }

    @PostMapping("/assistant")
    public ResponseEntity<byte[]> handleVoiceAssistant(@RequestParam(name = "file") MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No file uploaded");
        }

        try {
            byte[] response = voiceAssistantService.issueCommand(new ByteArrayResource(file.getBytes()));
            return ResponseEntity.ok(response);
        } catch (IOException ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "File upload failed");
        }
    }

}
