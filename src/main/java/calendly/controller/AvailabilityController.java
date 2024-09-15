package calendly.controller;

import calendly.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import calendly.services.IAvailabilityService;

@RestController
@RequestMapping("/api/v1/availability")
public class AvailabilityController {

    @Autowired
    private final IAvailabilityService availabilityService;

    private static final Logger logger = LoggerFactory.getLogger(AvailabilityController.class);


    public AvailabilityController(IAvailabilityService availabilityService) {
        this.availabilityService = availabilityService;
    }

    @Operation(summary = "Create user availability",
            description = "Creates availability for a user with either a daily or weekly schedule. The `type` field accepts two valid values: `daily` and `weekly`. " +
                    "`daily` allows specifying availability for particular dates, while `weekly` allows specifying availability on particular weekdays.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created",
                    content = @Content(schema = @Schema(implementation = AvailabilityResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content)
    })
    @PostMapping
    public ResponseEntity<AvailabilityResponse> createAvailability(@RequestBody CreateAvailabilityRequest request) {
        logger.info("CreateAvailability API received:" + request);
        try {
            AvailabilityResponse response = availabilityService.createAvailability(request);
            logger.info("CreateAvailability API response:" + response);
            return ResponseEntity.status(201).body(response);
        } catch (Exception e) {
            logger.info("CreateAvailability API exception:" + e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

    @Operation(summary = "Get user availability", description = "Retrieves the availability of a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = AvailabilityResponse.class))),
            @ApiResponse(responseCode = "404", description = "No availability found", content = @Content)
    })
    @GetMapping
    public ResponseEntity<AvailabilityResponse> getUserAvailability(@RequestParam Long userId) {
        AvailabilityRequest request = new AvailabilityRequest();
        request.setUserId(userId);

        logger.info("GetUserAvailability API received:" + request);
        try {
            AvailabilityResponse response = availabilityService.getUserAvailability(request);
            logger.info("GetUserAvailability API response:" + response);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.info("GetUserAvailability API exception:" + e.getMessage());
            return ResponseEntity.status(404).build();
        }

    }

    @Operation(summary = "Get overlap between two users", description = "Retrieves overlapping availability between two users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = OverlapResponse.class))),
            @ApiResponse(responseCode = "404", description = "No overlap found", content = @Content)
    })
    @GetMapping("/overlap")
    public ResponseEntity<OverlapResponse> getOverlap(@RequestParam Long firstUserId,
                                                      @RequestParam Long secondUserId) {
        logger.info("GetOverlap API received: firstUserId=" + firstUserId + ", secondUserId=" + secondUserId);

        // Create OverlapRequest from query parameters
        OverlapRequest request = new OverlapRequest();
        request.setFirstUserId(firstUserId);
        request.setSecondUserId(secondUserId);
        OverlapResponse response = availabilityService.getOverlap(request);
        logger.info("GetOverlap API response:" + response);
        if (response.getOverlapSlots().isEmpty()) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.ok(response);
    }
}

