import axios from "axios";
import React, { FormEvent, useEffect, useState } from "react";
import { useUser } from "../../utils/userInfo";
import { FaPencilAlt } from "react-icons/fa";
import { BsArrowReturnRight } from "react-icons/bs";

interface Props {
  boardId: string;
}

interface UserType {
  userId: string;
  userNameFirst: string;
  userNameLast: string;
}

interface CommentRequestBody {
  boardId: string;
  commentCreatorId: number;
  commentDepth: string;
  commentContent: string;
  parentCommentId?: string;
}

interface CommentType {
  boardId: string;
  commentContent: string;
  commentCreatedAt: string;
  commentCreatorId: string;
  commentDepth: string;
  commentId: string;
  commentUpdatedAt: string;
  parentCommentId?: string;
}

function BoardComment({ boardId }: Props) {
  const loginedUser = useUser();
  const [comment, setComment] = useState<string>('');
  const [comments, setComments] = useState<CommentType[]>([]);
  const [reply, setReply] = useState<{ [key: string]: string }>({});
  const [replyVisible, setReplyVisible] = useState<{ [key: string]: boolean }>({});
  const [users, setUsers] = useState<UserType[]>([]);

  useEffect(() => {
    async function getComments() {
      try {
        const res = await axios.get(`https://suhyeon.site/api/comment/byBoard/${boardId}`);
        const commentsData = res.data.data.content;
        setComments(commentsData);
      } catch (err) {
        console.log(err);
      }
    }
    async function getUsers() {
      try {
        const res = await axios.get(`https://suhyeon.site/api/address/user/all`);
        setUsers(res.data.data);
      } catch (err) {
        console.log(err);
      }
    }
    getComments();
    getUsers();
  }, [boardId]);

  const getComments = async () => {
    try {
      const res = await axios.get(`https://suhyeon.site/api/comment/byBoard/${boardId}`);
      const commentsData = res.data.data.content;
      setComments(commentsData);
    } catch (err) {
      console.log(err);
    }
  };

  const handleCommentSubmit = async (event: FormEvent) => {
    event.preventDefault();
    if (!loginedUser) {
      return alert("로그인이 필요합니다.");
    }

    const requestBody: CommentRequestBody = {
      "boardId": boardId,
      "commentCreatorId": Number(loginedUser.userId), // Convert userId to number
      "commentDepth": "0",
      "commentContent": comment,
    };

    try {
      const res = await axios.post("https://suhyeon.site/api/comment/", requestBody);
      console.log(res.data);
      setComment('');
      // 새로운 댓글 추가 후 댓글 목록을 다시 불러옴
      getComments();
    } catch (err) {
      console.log(err);
    }
  };

  const handleReplySubmit = async (event: FormEvent, parentId: string) => {
    event.preventDefault();
    if (!loginedUser) {
      return alert("로그인이 필요합니다.");
    }

    const requestBody: CommentRequestBody = {
      "boardId": boardId,
      "commentCreatorId": Number(loginedUser.userId), // Convert userId to number
      "commentDepth": "1",
      "commentContent": reply[parentId],
      "parentCommentId": parentId,
    };

    try {
      const res = await axios.post("https://suhyeon.site/api/comment/", requestBody);
      console.log(res.data);
      setReply({ ...reply, [parentId]: '' }); // 대댓글 입력 초기화
      // 대댓글 추가 후 댓글 목록을 다시 불러옴
      getComments();
    } catch (err) {
      console.log(err);
    }
  };

  const handleInputChange = (event: React.ChangeEvent<HTMLTextAreaElement>) => {
    setComment(event.target.value);
  };

  const handleReplyInputChange = (parentId: string, event: React.ChangeEvent<HTMLTextAreaElement>) => {
    setReply({ ...reply, [parentId]: event.target.value });
  };

  const handleCommentClick = (commentId: string) => {
    // 해당 댓글의 대댓글 창을 열거나 닫음
    setReplyVisible({ ...replyVisible, [commentId]: !replyVisible[commentId] });
    setReply({ ...reply, [commentId]: '' }); // 대댓글 입력 초기화
  };

  const getUserName = (userId: string) => {
    const user = users.find((user) => user.userId === userId);
    return user ? `${user.userNameLast}${user.userNameFirst}` : "";
  };

  return (
    <div>
      <form onSubmit={handleCommentSubmit}>
        <div className="w-full mb-4 border border-gray-200 rounded-sm bg-gray-50">
          <div className="px-4 py-2 bg-white rounded-t-sm">
            <textarea
              id="comment"
              rows={4}
              className="w-full px-0 text-sm text-gray-900 bg-white border-0"
              placeholder="댓글을 입력하세요"
              value={comment}
              onChange={handleInputChange}
              required
            />
          </div>
          <div className="flex items-center justify-end px-3 py-2 border-t">
            <button type="submit" className="inline-flex items-center py-2.5 px-4 text-xs font-medium text-center text-white bg-mainGreen rounded-lg hover:bg-blue-800">
              댓글 작성
            </button>
          </div>
        </div>
      </form>
      <div>
        <h1 className="font-semibold text-lg mb-2">댓글</h1>
        {comments.map((comment) => (
          <div key={comment.commentId} className="mb-2 border-b-2">
            <div className="flex items-center">
              <p>
                {getUserName(comment.commentCreatorId)} : {comment.commentContent}
              </p>
              <FaPencilAlt className="ml-2 cursor-pointer" size={10} onClick={() => handleCommentClick(comment.commentId)} />
            </div>
            {replyVisible[comment.commentId] && (
              <div className="ml-4">
                <form onSubmit={(e) => handleReplySubmit(e, comment.commentId)}>
                  <div className="w-full mb-4 border border-gray-200 rounded-sm bg-gray-50">
                    <div className="px-4 py-2 bg-white rounded-t-sm">
                      <textarea
                        id={`reply-${comment.commentId}`}
                        rows={1}
                        className="w-full px-0 text-sm text-gray-900 bg-white border-0"
                        placeholder="대댓글을 입력하세요"
                        value={reply[comment.commentId] || ''}
                        onChange={(e) => handleReplyInputChange(comment.commentId, e)}
                        required
                      />
                    </div>
                    <div className="flex items-center justify-end px-3 py-2 border-t">
                      <button type="submit" className="inline-flex items-center py-2.5 px-4 text-xs font-medium text-center text-white bg-mainGreen rounded-lg hover:bg-blue-800">
                        대댓글 달기
                      </button>
                    </div>
                  </div>
                </form>
              </div>
            )}
            {comments.filter((c) => c.parentCommentId === comment.commentId).map((reply) => (
              <div key={reply.commentId} className="ml-4">
                <p className="flex items-center">
                  <BsArrowReturnRight className="mr-2" size={12}/>
                  {getUserName(reply.commentCreatorId)} : {reply.commentContent}
                </p>
              </div>
            ))}
          </div>
        ))}
      </div>
    </div>
  );
}

export default BoardComment;