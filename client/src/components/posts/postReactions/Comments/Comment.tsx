import React, { useState } from 'react';
import { CommentInput } from './CommentInput';
import { CmntFunctionality } from './CmntFuncionality';
import { Grid } from '@mui/material';

export interface Comment {
  id: number;
  text: string;
  editable: boolean;
}

export const Comment: React.FC = () => {
  const [comments, setComments] = useState<Comment[]>([]);

  const handleSubmit = (text: string) => {
    const newComment = {id: Date.now(), text, editable: false};
    setComments([...comments, newComment]);
  }
  return (
    <>
      <Grid><CmntFunctionality comments={comments} setComments={setComments}/></Grid>
      <Grid><CommentInput onSubmit={handleSubmit}/></Grid>
    </>
  )
}
