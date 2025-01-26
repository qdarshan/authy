import React, { useState, memo } from "react";
import { Icon } from "@iconify/react";
import { Button } from "../ui/button";
import { Menu } from "lucide-react";
import { NavLink } from "react-router-dom";

const Nav = memo(() => {
  const [isOpen, setIsOpen] = useState(false);

  const toggleMenu = React.useCallback(() => {
    setIsOpen((prev) => !prev);
  }, []);

  const NavigationLink = ({
    to,
    children,
  }: {
    to: string;
    children: React.ReactNode;
  }) => (
    <div className="relative inline-block">
      <NavLink
        to={to}
        className="block relative 
          text-white 
          font-normal 
          hover:font-semibold 
          transition-all duration-200 
          px-2 py-1 
          hover:bg-ring/5
          after:content-[attr(data-text)]
          after:block 
          after:h-0 
          after:overflow-hidden 
          after:font-semibold
          after:invisible"
        data-text={children}
      >
        {children}
      </NavLink>
    </div>
  );

  return (
    <div className="flex justify-center w-full">
      <nav className="flex flex-wrap bg-secondary p-2 m-2 rounded sm:w-fit w-full">
        {/* Logo Section */}
        <div className="flex rounded items-center flex-shrink-0 text-white mr-6">
          <Icon
            className="h-8 w-8 p-1 rounded bg-primary-foreground text-primary font-bold"
            icon="emojione-monotone:elephant"
            height={32}
            width={32}
          />
          <span className="font-semibold text-lg tracking-tight p-1">
            Authy
          </span>
        </div>

        {/* Hamburger Menu Button */}
        <Button
          onClick={toggleMenu}
          className="md:hidden flex items-center px-3 py-2 border rounded bg-none ml-auto"
        >
          <Menu />
        </Button>

        {/* Navigation Links */}
        <div
          className={`${
            isOpen ? "block" : "hidden"
          } w-full md:flex md:items-center md:w-auto`}
        >
          <div className="text-sm md:flex-grow flex items-center space-x-4">
            <NavigationLink to="/">Home</NavigationLink>
            <NavigationLink to="/login">Login</NavigationLink>
          </div>
        </div>
      </nav>
    </div>
  );
});

Nav.displayName = "Nav";
export default Nav;
