import { createTheme, Tab, Tabs, ThemeProvider } from "@mui/material";
import { Box } from "grommet";
import React, { useState } from "react";
import { BottomSheet } from "react-spring-bottom-sheet";
import Typography from "@mui/material/Typography";
import { StyledText } from "./Common";
const theme = createTheme({
  status: {
    danger: "#e53e3e",
  },
  palette: {
    primary: {
      main: "#439652",
    },
    neutral: {
      main: "#64748B",
      contrastText: "#fff",
    },
  },
});

function TabPanel(props) {
  const { children, value, index, ...other } = props;

  return (
    <div
      role="tabpanel"
      hidden={value !== index}
      id={`simple-tabpanel-${index}`}
      aria-labelledby={`simple-tab-${index}`}
      {...other}
    >
      {value === index && (
        <Box sx={{ p: 3 }}>
          <Typography>{children}</Typography>
        </Box>
      )}
    </div>
  );
}
function a11yProps(index) {
  return {
    id: `simple-tab-${index}`,
    "aria-controls": `simple-tabpanel-${index}`,
  };
}

export const CourseReviewRank = ({ open, onDismiss }) => {
  const [value, setValue] = useState(0);
  const handleChange = (event, newValue) => {
    setValue(newValue);
  };
  return (
    <BottomSheet
      open={open}
      blocking={false}
      header={
        <ThemeProvider theme={theme}>
          <Tabs value={value} onChange={handleChange} centered>
            <Tab {...a11yProps(0)} label="랭킹" />
            <Tab {...a11yProps(1)} label="리뷰" />
          </Tabs>
        </ThemeProvider>
      }
      snapPoints={({ maxHeight }) => [maxHeight / 8, maxHeight * 0.6]}
    >
      <Box>
        <TabPanel value={value} index={0}>
          <Box>
            <StyledText text="시간별 순위" />
          </Box>
        </TabPanel>
        <TabPanel value={value} index={1}>
          배인수
        </TabPanel>
      </Box>
    </BottomSheet>
  );
};
