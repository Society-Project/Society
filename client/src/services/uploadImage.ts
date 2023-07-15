export const uploadImage = async (event: React.FormEvent<HTMLFormElement>) => {
  const form = event.currentTarget;
  const fileInput = Array.from(form.elements).find(
    (element: Element) => {
      return (
        element instanceof HTMLInputElement && element.name === "file" && element.files?.length
      );
    }
  ) as HTMLInputElement | undefined;

  if (!fileInput) {
    throw new Error("File input element not found or no files selected.");
  }

  const formData = new FormData();

  for (const file of Array.from(fileInput.files!)) {
    formData.append("file", file);
  }

  formData.append(
    "upload_preset",
    `${process.env.NEXT_PUBLIC_CLOUDINARY_UPLOAD_PRESET}`
  );

  const response = await fetch(`${process.env.NEXT_PUBLIC_CLOUDINARY_LINK}`, {
    method: "POST",
    body: formData,
  });

  if (!response.ok) {
    throw new Error("Image upload failed.");
  }

  const data = await response.json();

  return {
    imageUrl: data.secure_url,
    data,
  };
};