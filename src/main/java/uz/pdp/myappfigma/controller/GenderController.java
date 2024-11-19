package uz.pdp.myappfigma.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.myappfigma.dto.BaseResponse;
import uz.pdp.myappfigma.enums.Gender;

@RestController
@RequestMapping("/gender/v1")
public class GenderController {


    @Operation(summary = "Foydalanuvchi gender tanlash")
    @GetMapping("/by-gender")
    public BaseResponse<String> getUsersByGender(
            @RequestParam
            @Schema(description = "Foydalanuvchi jinsini tanlang", example = "MALE")
            Gender gender
    ) {

        return new BaseResponse<>(gender.getDisplayName());
    }
}
