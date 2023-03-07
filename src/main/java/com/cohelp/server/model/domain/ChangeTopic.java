package com.cohelp.server.model.domain;

import com.cohelp.server.model.entity.Activity;
import com.cohelp.server.model.entity.Help;
import com.cohelp.server.model.entity.Hole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeTopic {
    private Integer type;
    private Activity activity;
    private Help help;
    private Hole hole;
}
