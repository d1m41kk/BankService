package Controllers;

import Entities.Services.OperationService;
import Enums.OperationType;
import Models.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/operations")
public class OperationController {
    private final OperationService operationService;
    @Autowired
    public OperationController(OperationService operationService) {
        this.operationService = operationService;
    }

    @GetMapping("/get_operations/{id}")
    public List<Operation> getOperations(@PathVariable("id") String id) {
        return operationService.getOperations(id);
    }
}
