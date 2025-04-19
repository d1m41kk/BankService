package Controllers;

import Entities.Services.OperationService;
import Models.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/operations")
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "Успешный ответ"),
        @ApiResponse(responseCode = "404", description = "Операции не найдены"),
        @ApiResponse(responseCode = "500", description = "Ошибка сервера")
})
public class OperationController {
    private final OperationService operationService;
    @Autowired
    public OperationController(OperationService operationService) {
        this.operationService = operationService;
    }

    @GetMapping("/{id}")
    public List<Operation> getOperations(@PathVariable("id") String id) {
        return operationService.getOperations(id);
    }
}
