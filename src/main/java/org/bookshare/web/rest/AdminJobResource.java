package org.bookshare.web.rest;

import org.bookshare.job.GoodreadDataJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/jobs")
public class AdminJobResource {

    @Autowired
    private GoodreadDataJob goodreadDataJob;

    @PostMapping("/goodread/trigger")
    public ResponseEntity<?> triggerGoodread() {
        goodreadDataJob.updateBookMetadata();
        return ResponseEntity.ok(true);
    }
}
