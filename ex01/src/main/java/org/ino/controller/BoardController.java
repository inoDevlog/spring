package org.ino.controller;

import javax.inject.Inject;
import javax.print.attribute.standard.Severity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.ino.domain.BoardVO;
import org.ino.domain.Criteria;
import org.ino.domain.PageMaker;
import org.ino.service.BoardService;

@Controller
@RequestMapping("/board/*")
public class BoardController {

	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

	@Inject
	private BoardService service;

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public void registerGET(BoardVO board, Model model) throws Exception {

		logger.info("regist get..........");
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registPOST(BoardVO board, RedirectAttributes rttr) throws Exception {

		logger.info("regist post..........");
		logger.info(board.toString());

		service.regist(board);

		rttr.addFlashAttribute("msg", "success");

		// return "/board/success";
		return "redirect:/board/listAll";
	}

	@RequestMapping(value = "/listAll", method = RequestMethod.GET)
	public void listAll(Model model) throws Exception {
		logger.info("show all list..........");
		model.addAttribute("list", service.listAll());
	}

	@RequestMapping(value = "/read", method = RequestMethod.GET)
	public void read(@RequestParam("bno") int bno, Model model) throws Exception {
		model.addAttribute(service.read(bno));
	}

	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	public String remove(@RequestParam("bno") int bno, RedirectAttributes rttr) throws Exception {

		service.remove(bno);
		rttr.addFlashAttribute("msg", "Success");

		return "redirect:/board/listAll";
	}

	// 원래의 게시물 데이터를 읽어와서 Model에 넣어서 전달
	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public void modifyGET(int bno, Model model) throws Exception {
		model.addAttribute(service.read(bno));

	}

	// 실제 수정 작업 처리. 리턴 타입은 등록이나 삭제와 동일하게 처리
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String modifyPOST(BoardVO board, RedirectAttributes rttr) throws Exception {
		logger.info("modify post..........");

		service.modify(board);
		rttr.addFlashAttribute("msg", "Success");

		return "redirect:/board/listAll";
	}

	@RequestMapping(value = "/listCri", method = RequestMethod.GET)
	public void listAll(Criteria cri, Model model) throws Exception {

		logger.info("show list Page with Criteria..........");
		model.addAttribute("list", service.listCriteria(cri));
	}

	@RequestMapping(value = "/listPage", method = RequestMethod.GET)
	public void listPage(@ModelAttribute("cri") Criteria cri, Model model) throws Exception {

		logger.info(cri.toString());

		model.addAttribute("list", service.listCriteria(cri));
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		// pageMaker.setTotalCount(131);

		pageMaker.setTotalCount(service.listCountCriteria(cri));

		model.addAttribute("pageMaker", pageMaker);
	}

	@RequestMapping(value = "/readPage", method = RequestMethod.GET)
	public void read(@RequestParam("bno") int bno, @ModelAttribute("cri") Criteria cri, Model model) throws Exception {

		model.addAttribute(service.read(bno));
	}
}