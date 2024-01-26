package msk.extemulator.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import msk.extemulator.utils.SettingsConverter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;


@Getter
@Setter
@Entity
@Table(schema = "public")
public class Response {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "r_id")
    private Long id;

    @Column(name = "r_service", length = 50)
    private String service;

    @Column(name = "r_method", length = 50)
    private String method;

    @Column(name = "r_key", length = 200)
    private String key;

    @Column(name = "r_data")
    private String data;

    @Convert(converter = SettingsConverter.class)
    @Column(name = "r_settings")
    private Settings settings;
}
