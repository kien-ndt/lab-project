import { LoadingButton } from '@mui/lab';
import { Box, Button, ButtonProps, Chip, SxProps } from '@mui/material';
import React, { useRef, useState } from 'react';

export type Props = {
  loading?: boolean;
  labelNoFile: string;
  label: string;
  onSubmitFiles: (files: FileList) => void;
  variant?: ButtonProps['variant'];
  sx?: SxProps;
  size?: ButtonProps['size'];
  isDone?: boolean;
};

export const UploadFile = (props: Props) => {
  const { loading, labelNoFile, label, onSubmitFiles, isDone, ...buttonProps } =
    props;
  const fileRef = useRef<HTMLInputElement>(null);
  const [fileNames, setFileNames] = useState<string>();

  const onChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.target.files && e.target.files.length > 0) {
      setFileNames(
        Array.from(e.target.files)
          .reduce<Array<string>>((preVal, currentVal) => {
            preVal.push(currentVal.name);
            return preVal;
          }, [])
          .join(', '),
      );
    } else {
      setFileNames(undefined);
    }
  };

  const onDeleteFile = () => {
    if (fileRef.current) {
      fileRef.current.value = '';
      setFileNames(undefined);
    }
  };

  const submitFile = () => {
    if (fileRef.current && fileRef.current.files) {
      onSubmitFiles(fileRef.current.files);
      fileRef.current.value = '';
      setFileNames(undefined);
    }
  };

  return (
    <Box sx={{ display: 'inline-flex', alignItems: 'center' }}>
      {fileNames && (
        <Chip label={fileNames} onDelete={onDeleteFile} sx={{ mr: 1 }} />
      )}
      {loading ? (
        <LoadingButton loading {...buttonProps}>
          &nbsp;
        </LoadingButton>
      ) : (
        <>
          {!fileNames ? (
            <Button component="label" htmlFor="upload_file" {...buttonProps}>
              {labelNoFile}
            </Button>
          ) : (
            <Button
              {...buttonProps}
              onClick={() => {
                submitFile();
              }}
            >
              {label}
            </Button>
          )}
        </>
      )}
      <input
        id="upload_file"
        type="file"
        hidden
        ref={fileRef}
        onChange={onChange}
      />
    </Box>
  );
};
