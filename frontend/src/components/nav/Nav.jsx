import {Icon} from "@iconify/react";

const Nav = () => {
    return (
        <>
            <div className="flex justify-center w-full">
                <nav className="flex flex-wrap bg-secondary p-2 m-2 rounded-lg sm:w-fit w-full">
                    <div className="flex items-center flex-shrink-0 text-white mr-6">
                        <Icon className={"h-10 w-10 p-1 rounded bg-primary text-quaternary font-bold"}
                              icon="emojione-monotone:elephant"
                              strokeWidth={"2rem"} height="10rem" width="10rem"/>
                        {/*<Icon className={"h-10 w-10 p-2 rounded bg-primary text-red-600"} icon="arcticons:authenticator-pro"*/}
                        {/*      strokeWidth={"0.2rem"}/>*/}
                        <span className="font-semibold text-xl tracking-tight p-2">Authy</span>
                    </div>

                </nav>
            </div>
        </>
    );
}

export default Nav;