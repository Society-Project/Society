import { useState, useEffect } from 'react';
import { CommentInput } from './CommentInput';
import { CmntFunctionality } from './CmntFuncionality';
import { Grid } from '@mui/material';
import { getAllComments, postCommentFunction } from '../../../api';

export const Comment = (props: { postId: number }) => {
  const [comments, setComments] = useState<any>([]);

  useEffect(() => {
    (async() => {
      const response = await getAllComments(props.postId);
      if(!response) {
        return setComments([]);
      }
      setComments(response)
    })()
  }, [])

  return (
    <>
      <Grid>
        <CmntFunctionality comments={comments} setComments={setComments} />
      </Grid>
      <Grid>
        <CommentInput postId={props.postId} />
      </Grid>
    </>
  )
}
