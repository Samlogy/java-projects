export default {
    testMatch: ["**/__tests__/integration/**/*.test.tsx"],
    testEnvironment: "jsdom",
    transform: {
      "^.+\\.tsx?$": "ts-jest",
    },
  
    moduleNameMapper: {
      "\\.(css|less|sass|scss)$": "identity-obj-proxy",
      "^.+\\.svg$": "jest-transformer-svg",
      "^@/(.*)$": "<rootDir>/src/$1",
    },
  
    setupFilesAfterEnv: ["<rootDir>/jest.setup.ts"],
  };
  