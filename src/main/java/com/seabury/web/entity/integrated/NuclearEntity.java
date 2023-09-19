package com.seabury.web.entity.integrated;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NuclearEntity {
    String element_name;
    String nuclide;
    String weight_or_level_energy;
    String spin_and_parity;
    String abundance;
    String half_life;
    String sig_J32;
    String sig_J33;
    String sig_J40;
}