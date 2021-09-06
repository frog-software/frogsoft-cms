package com.frogsoft.frogsoftcms.controller.v1.request.home;

import com.frogsoft.frogsoftcms.model.article.Status;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
public class AnnouncementsSetRequest implements Serializable {
  private static final long serialVersionUID = 32974805623333756L;
  private List<Integer> articleIds;
}
