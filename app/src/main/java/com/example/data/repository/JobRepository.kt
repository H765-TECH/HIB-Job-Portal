package com.example.data.repository

import com.example.data.model.AgencyConsultation
import com.example.data.model.Job
import com.example.data.model.JobApplication
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

class JobRepository {

  private val _jobs = MutableStateFlow<List<Job>>(initialJobs)
  val jobs: Flow<List<Job>> = _jobs.asStateFlow()

  private val _applications = MutableStateFlow<List<JobApplication>>(initialApplications)
  val applications: Flow<List<JobApplication>> = _applications.asStateFlow()

  private val _agencyInquiries = MutableStateFlow<List<AgencyConsultation>>(emptyList())
  val agencyInquiries: Flow<List<AgencyConsultation>> = _agencyInquiries.asStateFlow()

  private val _subscribedEmails = MutableStateFlow<Set<String>>(emptySet())
  val subscribedEmails: Flow<Set<String>> = _subscribedEmails.asStateFlow()

  fun toggleBookmark(jobId: Long) {
    _jobs.value = _jobs.value.map { job ->
      if (job.id == jobId) job.copy(isBookmarked = !job.isBookmarked) else job
    }
  }

  fun submitApplication(
    job: Job,
    name: String,
    email: String,
    portfolio: String,
    resumeName: String,
    coverNote: String
  ): JobApplication {
    val formatter = SimpleDateFormat("MMM d, yyyy - h:mm a", Locale.getDefault())
    val dateStr = formatter.format(Date())

    val application = JobApplication(
      id = UUID.randomUUID().toString().take(8),
      jobId = job.id,
      jobTitle = job.title,
      company = job.company,
      applicantName = name,
      applicantEmail = email,
      portfolioUrl = portfolio.ifBlank { "Not provided" },
      resumeName = resumeName.ifBlank { "Resume_${name.replace(" ", "_")}.pdf" },
      coverNote = coverNote,
      appliedDate = dateStr,
      status = "Submitted"
    )

    _applications.value = listOf(application) + _applications.value
    return application
  }

  fun postJob(
    title: String,
    company: String,
    category: String,
    remoteType: String,
    location: String,
    salary: String,
    type: String,
    description: String,
    requirements: List<String>
  ): Job {
    val colors = listOf(0xFF2563EB, 0xFF7C3AED, 0xFF059669, 0xFF0284C7, 0xFFD97706)
    val initials = company.split(" ")
      .mapNotNull { it.firstOrNull()?.toString() }
      .take(3)
      .joinToString("")
      .uppercase()
      .ifBlank { "JOB" }

    val newJob = Job(
      id = System.currentTimeMillis(),
      title = title,
      company = company,
      companyInitials = initials,
      brandColorHex = colors.random(),
      category = category,
      remoteType = remoteType,
      location = location,
      salary = salary,
      type = type,
      posted = "Just now",
      isFeatured = true,
      description = description,
      requirements = requirements
    )

    _jobs.value = listOf(newJob) + _jobs.value
    return newJob
  }

  private var aiImportIndex = 0
  private val aiJobsPool = listOf(
    Job(
      id = 101L,
      title = "Senior AI & Data Engineer (Python + LangChain)",
      company = "OpenAI Partner Network",
      companyInitials = "AI",
      brandColorHex = 0xFF10A37F,
      category = "Software & Tech",
      remoteType = "100% WFA",
      location = "Global / Worldwide",
      salary = "$140,000 - $175,000",
      type = "Full-Time",
      posted = "Just now (AI Ingestion)",
      isFeatured = true,
      description = "Auto-imported via Make.com + OpenAI pipeline. Lead development of scalable LLM agent pipelines, vector database architectures, and agentic workflows.",
      requirements = listOf(
        "4+ years Python backend and microservice design",
        "Hands-on experience with Pinecone, Weaviate, or pgvector",
        "100% remote asynchronous team track record"
      ),
      isAiImported = true
    ),
    Job(
      id = 102L,
      title = "Head of Remote Operations & People",
      company = "Automattic Ecosystem",
      companyInitials = "AUTO",
      brandColorHex = 0xFF0073AA,
      category = "Executive & Fractional",
      remoteType = "100% WFA",
      location = "Global / Worldwide",
      salary = "$125,000 - $155,000",
      type = "Full-Time",
      posted = "Just now (AI Ingestion)",
      isFeatured = true,
      description = "Curated through automated feed ingestion. Lead distributed team operations, asynchronous workflows, and employee engagement strategies.",
      requirements = listOf(
        "5+ years scaling distributed remote organizations",
        "Strong knowledge of global contractor compliance and payroll",
        "Excellent written communication skills"
      ),
      isAiImported = true
    ),
    Job(
      id = 103L,
      title = "Principal Kotlin & Android Architect",
      company = "NextGen Mobile Labs",
      companyInitials = "NML",
      brandColorHex = 0xFF4F46E5,
      category = "Software & Tech",
      remoteType = "100% WFA",
      location = "Global / Worldwide",
      salary = "$150,000 - $190,000",
      type = "Full-Time",
      posted = "Just now (AI Ingestion)",
      isFeatured = true,
      description = "Architect state-of-the-art Android apps in Jetpack Compose, Kotlin Coroutines, and local-first reactive persistence models.",
      requirements = listOf(
        "6+ years deep Android engineering with Kotlin and Compose",
        "Deep understanding of performance profiling and reactive state",
        "Experience leading distributed remote mobile engineering teams"
      ),
      isAiImported = true
    ),
    Job(
      id = 104L,
      title = "Lifecycle & Retention Marketing Strategist",
      company = "Fintech Horizon",
      companyInitials = "FH",
      brandColorHex = 0xFF0D9488,
      category = "Marketing & Content",
      remoteType = "WFH Regional",
      location = "Work From Home (US/Canada)",
      salary = "$90,000 - $115,000",
      type = "Full-Time",
      posted = "Just now (AI Ingestion)",
      isFeatured = false,
      description = "Drive customer retention loops, automated email sequences, and push notification campaigns across 1M+ active app users.",
      requirements = listOf(
        "3+ years experience with Customer.io, Braze, or Klaviyo",
        "Data-driven experimentation and cohort retention modeling",
        "Strong copywriting and storytelling background"
      ),
      isAiImported = true
    )
  )

  fun triggerSimulatedAiImport(): Job {
    val baseJob = aiJobsPool[aiImportIndex % aiJobsPool.size]
    aiImportIndex++
    val importedJob = baseJob.copy(
      id = System.currentTimeMillis(),
      posted = "Just now (AI Pipeline)"
    )
    _jobs.value = listOf(importedJob) + _jobs.value
    return importedJob
  }

  fun submitAgencyConsultation(
    company: String,
    email: String,
    role: String,
    urgency: String,
    message: String
  ): AgencyConsultation {
    val formatter = SimpleDateFormat("MMM d, yyyy", Locale.getDefault())
    val consultation = AgencyConsultation(
      id = UUID.randomUUID().toString().take(6),
      companyName = company,
      workEmail = email,
      roleHiringFor = role,
      urgency = urgency,
      message = message,
      submittedAt = formatter.format(Date())
    )
    _agencyInquiries.value = listOf(consultation) + _agencyInquiries.value
    return consultation
  }

  fun subscribeNewsletter(email: String): Boolean {
    if (email.isBlank() || !email.contains("@")) return false
    _subscribedEmails.value = _subscribedEmails.value + email.trim()
    return true
  }

  companion object {
    val initialJobs = listOf(
      Job(
        id = 8L,
        title = "Full Stack AI & React Engineer (100% WFA - Pakistan & Global)",
        company = "Global Venture Labs",
        companyInitials = "GVL",
        brandColorHex = 0xFF10B981,
        category = "Software & Tech",
        remoteType = "100% WFA",
        location = "Global / Worldwide (Pakistan Eligible)",
        salary = "$65,000 - $90,000 /yr ($5,500 - $7,500/mo)",
        type = "Full-Time",
        posted = "1 hour ago",
        isFeatured = true,
        description = "100% remote asynchronous role with full support for global contractors. Open to software engineers in Pakistan and worldwide. USD payments disbursed via Payoneer, Wise, or direct bank transfer (Meezan, HBL, etc.).",
        requirements = listOf(
          "4+ years experience with React, Next.js, and TypeScript",
          "Hands-on experience integrating LLM APIs and Python backends",
          "Comfortable with asynchronous communication (Loom, Slack)",
          "Based anywhere worldwide with flexible 4h PKT / UTC+5 overlap"
        ),
        isOpenToPakistan = true,
        timezoneNotes = "PKT (UTC+5) / Asian & EMEA timezone friendly"
      ),
      Job(
        id = 9L,
        title = "Senior Python Backend Architect (FastAPI / AI)",
        company = "ScaleGlobal Cloud",
        companyInitials = "SGC",
        brandColorHex = 0xFF0284C7,
        category = "Software & Tech",
        remoteType = "100% WFA",
        location = "Global / Worldwide (Pakistan Eligible)",
        salary = "$75,000 - $100,000 /yr",
        type = "Full-Time",
        posted = "3 hours ago",
        isFeatured = true,
        description = "Build high-throughput microservices and vector indexing engines for global enterprise clients. Direct USD contractor agreement with health coverage and bi-annual team retreats.",
        requirements = listOf(
          "5+ years building distributed Python backends",
          "Deep knowledge of PostgreSQL, Redis, and Docker",
          "Track record in 100% remote asynchronous organizations"
        ),
        isOpenToPakistan = true,
        timezoneNotes = "100% Asynchronous with weekly syncs"
      ),
      Job(
        id = 10L,
        title = "Technical Content & Developer Advocate (WFA)",
        company = "DeveloperFlow",
        companyInitials = "DEV",
        brandColorHex = 0xFF8B5CF6,
        category = "Marketing & Content",
        remoteType = "100% WFA",
        location = "Global / Worldwide (Pakistan Eligible)",
        salary = "$55,000 - $75,000 /yr",
        type = "Full-Time",
        posted = "6 hours ago",
        isFeatured = false,
        description = "Write deep-dive technical tutorials, documentation, and SDK quickstarts for open-source AI developer tools. Open to engineers and technical writers across Pakistan and worldwide.",
        requirements = listOf(
          "Proven technical writing or developer relations portfolio",
          "Hands-on coding experience in JavaScript or Python",
          "Fluent written and spoken English"
        ),
        isOpenToPakistan = true,
        timezoneNotes = "Flexible Asian & EMEA hours"
      ),
      Job(
        id = 1L,
        title = "Senior Full Stack Engineer (Node + React)",
        company = "Gitlab Ecosystem",
        companyInitials = "GIT",
        brandColorHex = 0xFF1E293B,
        category = "Software & Tech",
        remoteType = "100% WFA",
        location = "Global / Worldwide",
        salary = "$110,000 - $140,000",
        type = "Full-Time",
        posted = "2 hours ago",
        isFeatured = true,
        description = "We are seeking a Senior Full Stack Engineer to join our decentralized product team. You will build high-concurrency microservices and responsive React interfaces for millions of developer teams.",
        requirements = listOf(
          "5+ years experience with Node.js & React",
          "Proven track record in remote-first team environments",
          "Deep understanding of PostgreSQL and Redis",
          "Experience with CI/CD pipelines and Kubernetes"
        ),
        isOpenToPakistan = true,
        timezoneNotes = "100% Asynchronous worldwide"
      ),
      Job(
        id = 2L,
        title = "Remote Growth Marketing Lead",
        company = "SaaS Scaleup",
        companyInitials = "SAAS",
        brandColorHex = 0xFF2563EB,
        category = "Marketing & Content",
        remoteType = "100% WFA",
        location = "Global / Worldwide",
        salary = "$85,000 - $105,000",
        type = "Full-Time",
        posted = "5 hours ago",
        isFeatured = false,
        description = "Manage paid acquisition channels, organic SEO content loops, and lifecycle email automation to scale our fast-growing SaaS customer base worldwide.",
        requirements = listOf(
          "3+ years performance marketing experience",
          "Proficiency with Google Analytics 4, Mixpanel, and Meta Ads",
          "Excellent analytical and copywriting skills",
          "Experience with international market localization"
        )
      ),
      Job(
        id = 3L,
        title = "Customer Success Manager (EMEA Timezones)",
        company = "CloudFlow Systems",
        companyInitials = "CFS",
        brandColorHex = 0xFF059669,
        category = "Customer Support",
        remoteType = "WFH Regional",
        location = "Work From Home (EMEA Region)",
        salary = "$60,000 - $75,000",
        type = "Full-Time",
        posted = "1 day ago",
        isFeatured = false,
        description = "Serve as the primary point of contact for mid-market enterprise accounts across Europe and Africa. Drive onboarding, feature adoption, and client retention.",
        requirements = listOf(
          "Fluency in English (German or French is a plus)",
          "2+ years in B2B SaaS account management",
          "Strong technical troubleshooting abilities",
          "Comfortable running video onboarding presentations"
        )
      ),
      Job(
        id = 4L,
        title = "Fractional Chief Marketing Officer (CMO)",
        company = "HIB Client Partners",
        companyInitials = "HIB",
        brandColorHex = 0xFF7C3AED,
        category = "Executive & Fractional",
        remoteType = "100% WFA",
        location = "Global / Worldwide",
        salary = "$120/hr - $150/hr",
        type = "Fractional / Contract",
        posted = "Just now",
        isFeatured = true,
        description = "Directly headhunted by HIB Recruitment Services. High-growth Series A startup seeking a fractional CMO to drive strategic brand positioning and global GTM launch.",
        requirements = listOf(
          "Prior C-suite experience in tech startups",
          "Proven capability in building GTM frameworks from 0 to 1",
          "10-15 hours per week availability",
          "Executive communication and investor relations alignment"
        )
      ),
      Job(
        id = 5L,
        title = "DevOps & Infrastructure Architect",
        company = "Vercel Ecosystem",
        companyInitials = "VER",
        brandColorHex = 0xFF0F172A,
        category = "Software & Tech",
        remoteType = "WFH Regional",
        location = "Work From Home (US/Canada)",
        salary = "$130,000 - $160,000",
        type = "Full-Time",
        posted = "2 days ago",
        isFeatured = false,
        description = "Maintain terraform deployments, Kubernetes clusters, and automated CI/CD deployment pipelines across multi-cloud environments.",
        requirements = listOf(
          "AWS Certified Solutions Architect",
          "Strong experience with Docker, K8s, and GitHub Actions",
          "On-call rotation availability with generous compensation",
          "Solid knowledge of network security and zero-trust VPCs"
        )
      ),
      Job(
        id = 6L,
        title = "Staff Mobile Engineer (Android & Compose)",
        company = "Stripe Ecosystem",
        companyInitials = "STRP",
        brandColorHex = 0xFF6366F1,
        category = "Software & Tech",
        remoteType = "100% WFA",
        location = "Global / Worldwide",
        salary = "$145,000 - $180,000",
        type = "Full-Time",
        posted = "3 days ago",
        isFeatured = true,
        description = "Develop mission-critical SDKs and modern payment interfaces for millions of mobile checkouts. 100% asynchronous, remote-first team culture.",
        requirements = listOf(
          "5+ years Android development experience with Kotlin",
          "Proficiency in Jetpack Compose, Coroutines, and Flows",
          "Strong testing discipline and security mindset"
        )
      ),
      Job(
        id = 7L,
        title = "Lead Product Designer (Design Systems)",
        company = "Linear Community",
        companyInitials = "LIN",
        brandColorHex = 0xFF5E6AD2,
        category = "Marketing & Content",
        remoteType = "Hybrid",
        location = "Hybrid Remote (London / EU)",
        salary = "$95,000 - $125,000",
        type = "Full-Time",
        posted = "3 days ago",
        isFeatured = false,
        description = "Craft meticulous user interfaces, micro-interactions, and design tokens for high-speed productivity software.",
        requirements = listOf(
          "Exceptional portfolio demonstrating software product design",
          "Figma master with deep auto-layout and variable skills",
          "Ability to write or read frontend code (React or Compose) is a plus"
        )
      )
    )

    val initialApplications = listOf(
      JobApplication(
        id = "APP-8421",
        jobId = 1L,
        jobTitle = "Senior Full Stack Engineer (Node + React)",
        company = "Gitlab Ecosystem",
        applicantName = "Alex Mercer",
        applicantEmail = "alex.mercer@gmail.com",
        portfolioUrl = "https://github.com/alexmercer",
        resumeName = "Alex_Mercer_Senior_Resume.pdf",
        coverNote = "Passionate full-stack developer with 6 years building distributed React/Node architectures.",
        appliedDate = "Nov 10, 2026",
        status = "Under Review"
      )
    )
  }
}
