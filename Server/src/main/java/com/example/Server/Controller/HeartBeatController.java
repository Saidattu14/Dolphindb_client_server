package com.example.Server.Controller;

import com.example.Server.Model.HeartBeatModel;
import com.example.Server.Services.HeartBeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/v1/heartbeat")
public class HeartBeatController {

    @Autowired
    HeartBeatService heartBeatService;

    @PostMapping("/create")
    public ResponseEntity<String> CreateData() {

        String res = heartBeatService.createData();
        switch (res) {
            case "Table Already Created":
                return new ResponseEntity<>(res, HttpStatus.CONFLICT);
            case "Table Failed to Create":
                return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
            case "Table Created Successfully":
                return new ResponseEntity<>(res, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);

    }
    @PostMapping("/insert")
    public ResponseEntity<String> InsertData(@RequestBody HeartBeatModel heartBeatModel) {

        String res = heartBeatService.insertData(heartBeatModel);
        if(res.equals("Record Inserted"))
        {
            return new ResponseEntity<>(res,HttpStatus.ACCEPTED);
        }
        else if(res.equals("Invalid Time error"))
        {
            return new ResponseEntity<>(res,HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(res,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/read")
    public ResponseEntity<String> ReadData(
            @RequestParam(required = false) String date,
            @RequestParam(required = false) String time
            ) {

        String res = heartBeatService.readData(date,time);
        if(Objects.equals(res, "Invalid Format Query Params"))
        {
            return new ResponseEntity<>("Invalid Format Query Params",HttpStatus.BAD_REQUEST);
        }
        else if(res == null)
        {
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(res,HttpStatus.OK);

    }
}
