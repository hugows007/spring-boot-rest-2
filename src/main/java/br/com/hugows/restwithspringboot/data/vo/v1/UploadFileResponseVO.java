package br.com.hugows.restwithspringboot.data.vo.v1;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UploadFileResponseVO extends RepresentationModel<UploadFileResponseVO> implements Serializable {

    private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private Long size;
}
