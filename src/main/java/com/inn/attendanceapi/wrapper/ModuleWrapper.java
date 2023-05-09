package com.inn.attendanceapi.wrapper;

import com.inn.attendanceapi.model.Module;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ModuleWrapper {

    private Integer id;

    private String name;

    public ModuleWrapper(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public ModuleWrapper(Module module){
        this.id = module.getId();
        this.name = module.getName();
    }


}
