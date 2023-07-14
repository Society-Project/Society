import SettingsPage from "@/components/Settings/SettingsPage";
import ProtectedRoutes from "@/ProtectedRoutes/ProtectedRoutes";

const settings = () => {
  return <ProtectedRoutes>
    <SettingsPage />
  </ProtectedRoutes>
}

export default settings