package model;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RowValue {

    private String seriesNumber;
    private String fileName;
    private String name;
    private String description;
    private String gender;
    private String attributes;
    private String uuid;
}
