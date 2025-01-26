import { NavLink, Route, Routes, useLocation } from "react-router-dom";
import Nav from "./components/nav/Nav";
import AppSidebar from "./components/nav/AppSidebar";
import { Toaster } from "./components/ui/toaster";
import App from "./App";
import Login from "./components/Login";
import Register from "./components/Register";
import Dashboard from "./components/Dashboard";
import {
  SidebarInset,
  SidebarProvider,
  SidebarTrigger,
} from "./components/ui/sidebar";
import { Separator } from "./components/ui/separator";
import {
  Breadcrumb,
  BreadcrumbItem,
  BreadcrumbLink,
  BreadcrumbList,
  BreadcrumbPage,
  BreadcrumbSeparator,
} from "./components/ui/breadcrumb";

const Lay = () => {
  return (
    <>
      <Toaster />
      <Routes>
        <Route path="/" element={<App />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/dashboard" element={<Dashboard />} />
      </Routes>
    </>
  );
};

const SidebarNav = () => {
  const location = useLocation();
  const path = location.pathname;
  const pathArray = path.split("/").filter((p) => p);
  return (
    <>
      <SidebarProvider>
        <AppSidebar />
        <SidebarInset>
          <header className="flex h-16 shrink-0 items-center gap-2 border-b px-4">
            <SidebarTrigger />
            <Separator orientation="vertical" className="mr-2 h-4" />
            <Breadcrumb>
              <BreadcrumbList>
                <BreadcrumbItem>
                </BreadcrumbItem>
                {pathArray.map((path, index) => {
                  return (
                    <BreadcrumbItem key={index}>
                      <BreadcrumbLink asChild>
                        <NavLink to={`/${path}`}>{path}</NavLink>
                      </BreadcrumbLink>
                      {index !== pathArray.length - 1 && (
                        <BreadcrumbSeparator>
                          <BreadcrumbPage>{"/"}</BreadcrumbPage>
                        </BreadcrumbSeparator>
                      )}
                    </BreadcrumbItem>
                  );
                })}
              </BreadcrumbList>
            </Breadcrumb>
          </header>
          <Lay />
        </SidebarInset>
      </SidebarProvider>
    </>
  );
};

const HorizontalNav = () => {
  return (
    <>
      <Nav />
      <Lay />
    </>
  );
};

const Layout = () => {
  const location = useLocation();
  const isDashboardPath = location.pathname.startsWith("/dashboard");

  return isDashboardPath ? <SidebarNav /> : <HorizontalNav />;
};
export default Layout;
