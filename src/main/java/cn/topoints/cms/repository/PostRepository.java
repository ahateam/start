package cn.topoints.cms.repository;

import cn.topoints.cms.domain.Post;
import cn.topoints.utils.data.rds.RDSRepository;

public class PostRepository extends RDSRepository<Post> {

	private static PostRepository ins;

	public static synchronized PostRepository getInstance() {
		if (null == ins) {
			ins = new PostRepository();
		}
		return ins;
	}

	private PostRepository() {
		super(Post.class);
	}
}
