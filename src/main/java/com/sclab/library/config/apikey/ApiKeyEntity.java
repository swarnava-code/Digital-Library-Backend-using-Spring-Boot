package com.sclab.library.config.apikey;//package com.sclab.library.config.somenew;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiKeyEntity {
    @Id
    private Long id;

    @Column(unique = true)
    private String keyValue;

    private String userType;
    private String roleId;
}
