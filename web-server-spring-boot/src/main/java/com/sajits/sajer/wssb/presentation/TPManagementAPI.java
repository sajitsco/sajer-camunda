package com.sajits.sajer.wssb.presentation;

import javax.validation.Valid;

import com.sajits.sajer.core.engine.STask;
import com.sajits.sajer.wssb.Utils;
import com.sajits.sajer.wssb.application.UseSajer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/p/tpmanagement")
public class TPManagementAPI {
    @Autowired
    UseSajer useSajer;
    
    @GetMapping("/task")
	ResponseEntity<?> task() {
        return ResponseEntity.ok(useSajer.getEngine().getTPManagement().tasks(Utils.getId()));
    }

    @PostMapping("/update-task")
    public ResponseEntity<STask> updateTask(@Valid @RequestBody STask task) {
        return ResponseEntity.ok(useSajer.getEngine().getTPManagement().updateTask(Utils.getId(), task));
    }

    @PostMapping("/complete-task")
    public ResponseEntity<String> completeTask(@Valid @RequestBody STask task) {
        return ResponseEntity.ok(useSajer.getEngine().getTPManagement().completeTask(task, Utils.getId()));
    }

    @GetMapping("/delegate-task")
	ResponseEntity<String> delegateTask(@RequestParam("task") String taskId, @RequestParam("user") String userId) {
        return ResponseEntity.ok(useSajer.getEngine().getTPManagement().delegateTask(taskId, userId));
    }

    @GetMapping("/resolve-task")
	ResponseEntity<String> resolveTask(@RequestParam("task") String taskId) {
        return ResponseEntity.ok(useSajer.getEngine().getTPManagement().resolveTask(taskId));
    }

    @GetMapping("/delete-task")
	ResponseEntity<String> deleteTask(@RequestParam("task") String taskId, @RequestParam("reason") String reason) {
        return ResponseEntity.ok(useSajer.getEngine().getTPManagement().deleteTask(taskId, reason));
    }

    @GetMapping("/set-candidate")
	ResponseEntity<String> setCandidate(@RequestParam("task") String taskId, @RequestParam("candidate") String candidate) {
        return ResponseEntity.ok(useSajer.getEngine().getTPManagement().setCandidateForTask(taskId, candidate));
    }

    @GetMapping("/tpmoptions")
	ResponseEntity<?> setTPMOptions() {
        return ResponseEntity.ok(useSajer.getEngine().getTPManagement().setTPMOptions(Utils.getId()));
    }

    @GetMapping("/add-tp")
	ResponseEntity<String> addTP(@RequestParam("id") String id, @RequestParam("type") String type, @RequestParam("name") String name) {
        return ResponseEntity.ok(useSajer.getEngine().getTPManagement().addTP(id, type, name, Utils.getId()));
    }

    @GetMapping("/dspreview")
	ResponseEntity<String> getDS(@RequestParam("id") String id) {
        return ResponseEntity.ok(useSajer.getEngine().getTPManagement().getDS(id, Utils.getId()));
    }
}
