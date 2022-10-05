import { styled } from "@mui/material/styles";
import { Button } from "@mui/material";

export const BootstrapButton = styled(Button)({
  boxShadow: "4px 4px 4px -4px rgb(0 0 0 / 0.2)",
  textTransform: "none",
  fontSize: 16,
  fontWeight: "bold",
  padding: "6px 12px",
  color: "white",
  width: "276px",
  height: "56px",
  margin: "10px",
  backgroundColor: "#64CCBE",
  borderRadius: "10px",
  fontFamily: ["sans-serif"],
  "&:hover": {
    backgroundColor: "#64CCBE",
    boxShadow: "none",
    color: "white",
  },
});

export const RegisterButton = styled(Button)({
  boxShadow: "none",
  textTransform: "none",
  fontSize: 14,
  fontWeight: "bold",
  padding: "6px 12px",
  color: "white",
  width: "75%",
  height: "5vh",
  margin: "10px",
  backgroundColor: "#64CCBE",
  fontFamily: ["sans-serif"],
  "&:hover": {
    backgroundColor: "#64CCBE",
    boxShadow: "none",
    color: "white",
  },
});

export const PauseButton = styled(Button)({
  boxShadow: "none",
  textTransform: "none",
  fontSize: 14,
  fontWeight: "bold",
  padding: "6px 12px",
  color: "white",
  width: "50%",
  height: "5vh",
  margin: "10px",
  backgroundColor: "#64CCBE",
  fontFamily: ["sans-serif"],
  "&:hover": {
    backgroundColor: "#64CCBE",
    boxShadow: "none",
    color: "white",
  },
});

export const WhiteButton = styled(Button)({
  boxShadow: "none",
  textTransform: "none",
  border: "1px solid #64CCBE",
  fontSize: 14,
  fontWeight: "bold",
  padding: "6px 12px",
  color: "#64CCBE",
  width: "50%",
  height: "5vh",
  margin: "10px",
  backgroundColor: "white",
  fontFamily: ["sans-serif"],
  "&:hover": {
    backgroundColor: "white",
    boxShadow: "none",
    color: "#64CCBE",
  },
});

export const ExitButton = styled(Button)({
  boxShadow: "none",
  textTransform: "none",
  fontSize: 14,
  fontWeight: "bold",
  padding: "6px 12px",
  color: "white",
  width: "50%",
  height: "5vh",
  margin: "10px",
  backgroundColor: "#F29393",
  fontFamily: ["sans-serif"],
  "&:hover": {
    backgroundColor: "#F29393",
    boxShadow: "none",
    color: "white",
  },
});
