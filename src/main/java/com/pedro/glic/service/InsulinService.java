package com.pedro.glic.service;

import com.pedro.glic.dto.InsulinRequestDTO;
import com.pedro.glic.dto.InsulinResponseDTO;
import com.pedro.glic.entity.Insulin;
import com.pedro.glic.entity.User;
import com.pedro.glic.repository.InsulinRepository;
import com.pedro.glic.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InsulinService {

    private final InsulinRepository insulinRepository;
    private final UserRepository userRepository;

    public InsulinService(InsulinRepository insulinRepository, UserRepository userRepository) {
        this.insulinRepository = insulinRepository;
        this.userRepository = userRepository;
    }

    public InsulinResponseDTO createInsulin(Long userId, InsulinRequestDTO dto){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Insulin insulin = new Insulin(
                dto.name(), dto.description(), dto.units(), dto.price(),
                dto.quantity(), dto.openingDate(), dto.purchaseDate(),
                dto.type(), dto.format()
        );
        insulin.setUser(user);
        Insulin insulinSaved = insulinRepository.save(insulin);
        return InsulinResponseDTO.toDTO(insulinSaved);
    }

    public List<InsulinResponseDTO> findAllByUserId(Long userId){
        List<Insulin> insulins = insulinRepository.findAllByUserId(userId);
        return insulins.stream().map(InsulinResponseDTO::toDTO).toList();
    }

    public InsulinResponseDTO findById(Long userId, Long insulinId){
        Insulin insulin = getInsulinAndVerifyOwnership(userId, insulinId);
        return InsulinResponseDTO.toDTO(insulin);
    }

    public InsulinResponseDTO updateInsulin(Long userId, Long insulinId, InsulinRequestDTO dto){
        Insulin insulin = getInsulinAndVerifyOwnership(userId, insulinId);
        insulin.updateFrom(dto);
        return InsulinResponseDTO.toDTO(insulinRepository.save(insulin));
    }

    public void deleteInsulin(Long userId, Long insulinId){
        Insulin insulin = getInsulinAndVerifyOwnership(userId, insulinId);
        insulinRepository.delete(insulin);
    }

    private Insulin getInsulinAndVerifyOwnership(Long userId, Long insulinId) {
        Insulin insulin = insulinRepository.findById(insulinId)
                .orElseThrow(() -> new RuntimeException("Insulin not found"));
        if (!insulin.getUser().getId().equals(userId)) {
            throw new RuntimeException("This insulin does not belong to this user");
        }
        return insulin;
    }
}