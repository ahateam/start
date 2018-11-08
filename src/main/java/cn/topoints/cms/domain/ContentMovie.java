package cn.topoints.cms.domain;

import cn.topoints.utils.data.rds.RDSAnnEntity;

/**
 * 跟Content一摸一样，分表而已
 */
@RDSAnnEntity(alias = "tb_content_movie")
public class ContentMovie extends Content {
}
