package cn.topoints.cms.domain;

import cn.topoints.utils.data.rds.RDSAnnEntity;
import cn.topoints.utils.data.rds.RDSAnnField;
import cn.topoints.utils.data.rds.RDSAnnID;

/**
 * 内容源</br>
 * 
 * 像电视剧这样的播放源，其数据量不小，放表上存不下，只能放到单独的表里</br>
 * 电影之类播放源数据比较小的数据，可以直接内嵌content表</br>
 * 
 * 例如：一个youku播放链接平均125个字符，100集的电视剧，就需要12500个字符</br>
 * 我们的一个内容，可能还会对应youku，tudou等多个播放源，内容就会非常大，因此用JSON格式存储，JSON可以存放非常大的数据，目前RDS默认配置似乎是1G</br>
 *
 */
@RDSAnnEntity(alias = "tb_content_source")
public class ContentSource {

	@RDSAnnID
	@RDSAnnField(column = RDSAnnField.ID)
	public Long contentId;

	@RDSAnnID
	@RDSAnnField(column = "VARCHAR(64)")
	public String sourceKey;

	/**
	 * 最多1万字符
	 */
	@RDSAnnField(column = RDSAnnField.TEXT)
	public String source;

}
