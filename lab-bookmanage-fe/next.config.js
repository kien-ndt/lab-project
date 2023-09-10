/** @type {import('next').NextConfig} */
const nextConfig = {  env:{
  NEXT_PUBLIC_API_HOST: process.env.API_HOST || "http://localhost:8080/v1"
}}

module.exports = nextConfig
