package com.frogsoft.frogsoftcms.repository.file;

import com.frogsoft.frogsoftcms.model.file.Material;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<Material,Long> {
}
