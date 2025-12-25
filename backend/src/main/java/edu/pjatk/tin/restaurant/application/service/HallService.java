package edu.pjatk.tin.restaurant.application.service;

import edu.pjatk.tin.restaurant.application.mapper.HallMapper;
import edu.pjatk.tin.restaurant.domain.model.Hall;
import edu.pjatk.tin.restaurant.domain.model.RestaurantTable;
import edu.pjatk.tin.restaurant.domain.repository.HallRepository;
import edu.pjatk.tin.restaurant.domain.repository.RestaurantTableRepository;
import edu.pjatk.tin.restaurant.domain.value.HallDimensions;
import edu.pjatk.tin.restaurant.web.dto.request.CreateHallDto;
import edu.pjatk.tin.restaurant.web.dto.request.ResizeHallDto;
import edu.pjatk.tin.restaurant.web.dto.request.UpdateHallInfoDto;
import edu.pjatk.tin.restaurant.web.dto.response.HallDetailsDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HallService {
    private final HallRepository hallRepository;
    private final RestaurantTableRepository restaurantTableRepository;

    public HallService(HallRepository hallRepository, RestaurantTableRepository restaurantTableRepository) {
        this.hallRepository = hallRepository;
        this.restaurantTableRepository = restaurantTableRepository;
    }

    public HallDetailsDto createHall(CreateHallDto dto){
        Hall hall = HallMapper.toHall(dto);
        Hall savedHall = hallRepository.save(hall);

        return HallMapper.toHallDetailsDto(savedHall);
    }

    public HallDetailsDto resizeHall(Long hallId, ResizeHallDto dto){
        Hall hall = hallRepository.findById(hallId)
                .orElseThrow(() -> new RuntimeException("Hall with id " + hallId + " not found"));

        List<RestaurantTable> hallTables = restaurantTableRepository.findAllByHallId(hallId);
        HallDimensions newDimensions = HallDimensions.of(dto.newLength(), dto.newWidth());

        validateTablesWithinNewDimensions(hallTables, newDimensions);

        hall.resize(newDimensions);
        Hall updatedHall = hallRepository.save(hall);

        return HallMapper.toHallDetailsDto(updatedHall);
    }

    private void validateTablesWithinNewDimensions(List<RestaurantTable> tables, HallDimensions newDimensions){
        for(RestaurantTable table : tables){
            RestaurantTable.validatePosition(table.getPosition(), table.getTableType().getDimensions(), newDimensions);
        }
    }

    public HallDetailsDto changeHallInfo(Long hallId, UpdateHallInfoDto dto){
        Hall hall = hallRepository.findById(hallId)
                .orElseThrow(() -> new RuntimeException("Hall with id " + hallId + " not found"));

        hall.changeName(dto.name());
        hall.changeFloorNumber(dto.floor());
        Hall saved = hallRepository.save(hall);

        return HallMapper.toHallDetailsDto(saved);
    }

    public HallDetailsDto getHallDetails(Long hallId){
        Hall hall = hallRepository.findById(hallId)
                .orElseThrow(() -> new RuntimeException("Hall with id " + hallId + " not found"));

        return HallMapper.toHallDetailsDto(hall);
    }

    public List<HallDetailsDto> getAllHalls(){
        List<Hall> halls = hallRepository.findAll();
        return halls.stream().map(HallMapper::toHallDetailsDto).toList();
    }

    public void deleteHall(Long hallId){
        hallRepository.deleteById(hallId);
    }
}
