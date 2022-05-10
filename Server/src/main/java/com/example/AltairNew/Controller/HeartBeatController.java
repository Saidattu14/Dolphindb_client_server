package com.example.AltairNew.Controller;



import com.example.AltairNew.Model.HeartBeatModel;
import com.example.AltairNew.Services.HeartBeatService;
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
        if(res.equals("Insert record"))
        {
            return new ResponseEntity<>("Record Inserted",HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(null,HttpStatus.NOT_ACCEPTABLE);
    }

    @GetMapping("/read")
    public ResponseEntity<String> ReadData(
            @RequestParam(required = false) String date,
            @RequestParam(required = false) String time
            ) {

        heartBeatService.readData(date,time);
        return new ResponseEntity<>(null,HttpStatus.NOT_ACCEPTABLE);
    }
    @PutMapping("/update")
    public ResponseEntity<String> UpdateData(
            @RequestParam(required = false,defaultValue = "0") int page,
            @RequestParam(required = false,defaultValue = "10") int page_size,
            @RequestParam(required = false,defaultValue = "") String name) {


        return new ResponseEntity<>(null,HttpStatus.NOT_ACCEPTABLE);
    }
    @DeleteMapping("/delete")
    public ResponseEntity<String> DeleteData(
            @RequestParam(required = false,defaultValue = "0") int page,
            @RequestParam(required = false,defaultValue = "10") int page_size,
            @RequestParam(required = false,defaultValue = "") String name) {


        return new ResponseEntity<>(null,HttpStatus.NOT_ACCEPTABLE);
    }

}
