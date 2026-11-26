package ru.practicum.shareit.item.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ItemDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotNull
    private Boolean available;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long requestId;
}
