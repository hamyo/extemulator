package msk.extemulator.domain;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Settings {
    private Integer delay;
    private Integer responseCode;

    public boolean needPause() {
        return delay != null && delay > 0;
    }

    public static Settings ofDefault() {
        return new Settings(0, 200);
    }
}
