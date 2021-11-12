package com.hrl.blog.Service;

import com.hrl.blog.Dao.CommentRepostitory;
import com.hrl.blog.entities.Comment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class CommentServiceImpl implements CommentService {

    private List<Comment> tempReplys = new ArrayList<>();
    @Autowired
    private CommentRepostitory commentRepostitory;

    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        Sort sort =Sort.by("createTime");
        List<Comment> comments = commentRepostitory.findByBlogIdAndCommentParentNull(blogId,sort);
        return eachComment(comments);
    }

    private List<Comment> eachComment(List<Comment> comments) {
        List<Comment> commentsView = new ArrayList<>();
        for (Comment comment : comments){
            Comment c = new Comment();
            BeanUtils.copyProperties(comment,c);
            commentsView.add(c);
        }
        combineChildren(commentsView);
        return commentsView;
    }

    private void combineChildren(List<Comment> commentsView) {

        for (Comment comment : commentsView){
            List<Comment> replys = comment.getReplyComments();
            for (Comment reply : replys){
                //循环迭代，找出子代，存放在tempReply中
                recursively(reply);
            }
            //修改顶级节点的reply集合为迭代处理后的集合
            comment.setReplyComments(tempReplys);
            //清除临时存放
            tempReplys = new ArrayList<>();
        }
    }

    private void recursively(Comment reply) {
        tempReplys.add(reply);//顶点添加临时存放的集合
        if(reply.getReplyComments().size()>0){
            List<Comment> replys = reply.getReplyComments();
            for (Comment comment : replys){
                tempReplys.add(comment);
                if(comment.getReplyComments().size()>0){
                    recursively(comment);
                }
            }
        }
    }


    @Transactional
    @Override
    public Comment saveComment(Comment comment) {
        Long commentParent = comment.getCommentParent().getId();
        if(commentParent!=-1){
            comment.setCommentParent(commentRepostitory.getById(commentParent));
        }else{
            comment.setCommentParent(null);
        }
        comment.setCreateTime(new Date());


        return commentRepostitory.save(comment);
    }
}
