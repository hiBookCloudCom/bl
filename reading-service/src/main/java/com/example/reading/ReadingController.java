package com.example.reading;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/reading")
@RequiredArgsConstructor
public class ReadingController {

    private final ReadingService readingService;
    private final ReadingMapper mapper;

    // POST /api/reading
    // body: { "userId":1, "bookId":10, "status":"WANT_TO_READ", "rating":5, ... }
    @PostMapping
    public ResponseEntity<ReadingDTO> create(@RequestBody ReadingDTO dto) {
        Reading saved = readingService.addOrUpdateEntry(dto.getUserId(), dto.getBookId(), dto.getStatus());

        // opciono: ako želiš da POST odmah upiše i ove vrednosti
        if (dto.getProgressPages() != null || dto.getProgressPercent() != null) {
            saved = readingService.updateProgress(dto.getUserId(), dto.getBookId(),
                    dto.getProgressPages(), dto.getProgressPercent());
        }
        if (dto.getRating() != null) {
            saved = readingService.updateRating(dto.getUserId(), dto.getBookId(), dto.getRating());
        }
        if (dto.getNotes() != null) {
            saved = readingService.updateNotes(dto.getUserId(), dto.getBookId(), dto.getNotes());
        }

        ReadingDTO out = mapper.toDto(saved);
        return ResponseEntity
                .created(URI.create("/api/reading/" + out.getUserId() + "/" + out.getBookId()))
                .body(out);
    }

    // GET /api/reading?userId=1
    // GET /api/reading?userId=1&status=READ
    @GetMapping
    public ResponseEntity<List<ReadingDTO>> getReading(
            @RequestParam(name = "userId") Integer userId,
            @RequestParam(name = "status", required = false) ReadingStatus status
    ) {
        List<Reading> list = (status == null)
                ? readingService.getMyReadingList(userId)
                : readingService.getMyReadingListByStatus(userId, status);

        return ResponseEntity.ok(list.stream().map(mapper::toDto).toList());
    }

    // GET /api/reading/1/10
    @GetMapping("/{userId}/{bookId}")
    public ResponseEntity<ReadingDTO> getOne(@PathVariable("userId") Integer userId,
                                             @PathVariable("bookId") Integer bookId) {
        return ResponseEntity.ok(mapper.toDto(readingService.getEntry(userId, bookId)));
    }

    // PUT /api/reading/1/10
    // body: { "status":"READ", "rating":4, "progressPages":120, "notes":"..." }
    @PutMapping("/{userId}/{bookId}")
    public ResponseEntity<ReadingDTO> update(@PathVariable("userId") Integer userId,
                                             @PathVariable("bookId") Integer bookId,
                                             @RequestBody ReadingDTO dto) {

        if (dto.getStatus() != null) {
            readingService.updateStatus(userId, bookId, dto.getStatus());
        }
        if (dto.getProgressPages() != null || dto.getProgressPercent() != null) {
            readingService.updateProgress(userId, bookId, dto.getProgressPages(), dto.getProgressPercent());
        }
        if (dto.getRating() != null) {
            readingService.updateRating(userId, bookId, dto.getRating());
        }
        if (dto.getNotes() != null) {
            readingService.updateNotes(userId, bookId, dto.getNotes());
        }

        return ResponseEntity.ok(mapper.toDto(readingService.getEntry(userId, bookId)));
    }

    // DELETE /api/reading/1/10
    @DeleteMapping("/{userId}/{bookId}")
    public ResponseEntity<Void> delete(@PathVariable("userId") Integer userId,
                                       @PathVariable("bookId") Integer bookId) {
        readingService.removeEntry(userId, bookId);
        return ResponseEntity.noContent().build();
    }

    // GET /api/reading/count?userId=1&status=READ
    @GetMapping("/count")
    public ResponseEntity<Long> count(
            @RequestParam(name = "userId") Integer userId,
            @RequestParam(name = "status") ReadingStatus status
    ) {
        return ResponseEntity.ok(readingService.countByStatus(userId, status));
    }
}
