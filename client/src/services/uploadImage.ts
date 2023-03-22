export const uploadImage = async (event: React.FormEvent<HTMLFormElement>) => {
  const form = event.currentTarget;
  const fileInput: File[] | undefined = Array.from(form.elements).find(
    ({ name }) => name === "file"
  );

  const formData = new FormData();

  for (const file of fileInput.files) {
    formData.append("file", file);
  }

  formData.append(
    "upload_preset",
    `${process.env.NEXT_PUBLIC_CLOUDINARY_UPLOAD_PRESET}`
  );

  const data = await fetch(`${process.env.NEXT_PUBLIC_CLOUDINARY_LINK}`, {
    method: "POST",
    body: formData,
  }).then((res) => res.json());

  return {
    imageUrl: data.secure_url,
    data,
  };
};
