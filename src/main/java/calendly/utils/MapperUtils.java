package calendly.utils;

import calendly.dto.AvailabilityResponse;
import calendly.dto.CreateAvailabilityRequest;
import calendly.dto.DailySchedule;
import calendly.dto.WeeklySchedule;
import calendly.model.AvailabilityModel;
import calendly.model.Schedule;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class MapperUtils {

    @Autowired
    private ModelMapper modelMapper;

    public AvailabilityModel convertCreateAvailabilityRequestToAvailabilityModel(CreateAvailabilityRequest request) {
        AvailabilityModel model = modelMapper.map(request, AvailabilityModel.class);
        return model;
    }

    public AvailabilityResponse mapAvailabilityResponseModelToDTO(AvailabilityModel response) {
        // Get the lists
        List<Schedule> dailySchedules = AvailabilityUtils.getDailySchedules(response);
        List<Schedule> weeklySchedules = AvailabilityUtils.getWeeklySchedules(response);

        List<calendly.dto.Schedule> dailyDto= dailySchedules.stream()
                .map(schedule -> modelMapper.map(schedule, DailySchedule.class))
                .collect(Collectors.toList());

        List<calendly.dto.Schedule> weeklyDto= weeklySchedules.stream()
                .map(schedule -> modelMapper.map(schedule, WeeklySchedule.class))
                .collect(Collectors.toList());

        AvailabilityResponse responseDTO = AvailabilityResponse.builder()
                .dailySchedule(dailyDto)
                .weeklySchedule(weeklyDto)
                .userId(response.getUserId())
                .build();
        return responseDTO;
    }

}
