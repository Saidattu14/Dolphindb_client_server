package com.example.Altair.Controller;


import com.example.Altair.Model.HeartBeatModel;
import com.example.Altair.Services.HeartBeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/heartbeat")
public class HeartBeatController {

    @Autowired
    HeartBeatService heartBeatService;

    @PostMapping("/create")
    public ResponseEntity<String> CreateData() {

        String res = heartBeatService.createData();
        if(res.equals("Table Already Created"))
        {
            return new ResponseEntity<>(res,HttpStatus.CONFLICT);
        }
        else if(res.equals("Table Failed to Create"))
        {
            return new ResponseEntity<>(res,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        else if(res.equals("Table Created Successfully"))
        {
            return new ResponseEntity<>(res,HttpStatus.CREATED);
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
        if(res ==  "Invalid Format Query Params")
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
