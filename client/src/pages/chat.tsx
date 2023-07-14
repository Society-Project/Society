import Chat from "@/components/chat/Chat";
import ProtectedRoutes from "@/ProtectedRoutes/ProtectedRoutes";

const chat = () => {
  return <ProtectedRoutes>
    <Chat />
  </ProtectedRoutes>
}

export default chat