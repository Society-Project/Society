"use client";
import styled from "styled-components";

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <html lang="en">
      <Body>{children}</Body>
    </html>
  );
}

const Body = styled.body`
  margin: 0;
`;
