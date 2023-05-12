import { useEffect } from "react";
import Chat from "@/components/chat/Chat";
import { ValidateUser } from "./settings";

const chat = () => {
  useEffect(() => {
    ValidateUser();
  }, [])

  return <Chat />
}

export default chat